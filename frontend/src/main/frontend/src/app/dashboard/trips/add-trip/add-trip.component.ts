import {Component, ElementRef, Inject, NgZone, OnInit, ViewChild} from "@angular/core";
import {MdDialog, MdDialogRef} from "@angular/material";
import {SearchFriendsComponent} from "../../friends/search-friends/search-friends.component";
import {FormControl} from "@angular/forms";
import {MapsAPILoader} from "angular2-google-maps/core";
import {Trip} from "../../../model/trip";
import {FriendDetailsComponent} from "../../friends/friend-details/friend-details.component";
import {UserService} from "../../../services/user.service";
import {User} from "../../../model/user";
import {DOCUMENT} from "@angular/platform-browser";
import {FriendService} from "../../../services/friend.service";

@Component({
    moduleId: module.id,
    selector: 'add-trip-cmp',
    templateUrl: 'add-trip.component.html'
})
export class AddTripComponent implements OnInit {
    trip: Trip;
    friends: User[];
    public searchControl: FormControl;
    public zoom: number;
    selectedOption: string;

    @ViewChild("search")
    public searchElementRef: ElementRef;

    constructor(
        public dialog: MdDialog,
        public dialogRef: MdDialogRef<AddTripComponent>,
        private mapsAPILoader: MapsAPILoader,
        private ngZone: NgZone,
        @Inject(DOCUMENT) private document,
        private friendService: FriendService) { }

    ngOnInit() {
        if (+document.location.port == 4200) {
            // for only frontend development purposes
            this.friends = [
                {
                    id: 1,
                    username: 'roman33',
                    email: 'romy@mail.com',
                    firstName: 'Roman',
                    lastName: 'Nowak',
                    birthDate: '21.07.1989',
                    gender: 'MALE',
                    level: 'BEGINNER',
                    country: 'Polska',
                    city: 'Gliwice',
                    photo: null,
                    role: 'MUSHROOMER'
                },
                {
                    id: 2,
                    username: 'thomas22',
                    email: 'tomy22@mail.com',
                    firstName: 'Tom',
                    lastName: 'Goreing',
                    birthDate: '06.11.1991',
                    gender: 'MALE',
                    level: 'BEGINNER',
                    country: 'Germany',
                    city: 'Berlin',
                    photo: null,
                    role: 'MUSHROOMER'
                }
            ];
        } else {
            this.friendService.getAll().subscribe(
                result => this.friends = result
            );
        }
        this.trip = new Trip();
        this.trip.radius = 1000;
        this.searchControl = new FormControl();
        this.setCurrentPosition();
        //load Places Autocomplete
        this.mapsAPILoader.load().then(() => {
            let autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
                types: ["address"]
            });
            autocomplete.addListener("place_changed", () => {
                this.ngZone.run(() => {
                    //get the place result
                    let place: google.maps.places.PlaceResult = autocomplete.getPlace();
                    //verify result
                    if (place.geometry === undefined || place.geometry === null) {
                        return;
                    }
                    //set latitude, longitude and zoom
                    this.trip.coordinateX = place.geometry.location.lat();
                    this.trip.coordinateY = place.geometry.location.lng();
                    this.zoom = 12;
                });
            });
        });
    }

    private setCurrentPosition() {
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition((position) => {
                this.trip.coordinateX = position.coords.latitude;
                this.trip.coordinateY = position.coords.longitude;
                this.zoom = 12;
            });
        }
    }

    setPlace(place) {
    }

    circleDragEnd($event: any) {
        this.trip.coordinateX = $event.coords.lat;
        this.trip.coordinateY = $event.coords.lng;
        var latlng = new google.maps.LatLng(this.trip.coordinateX, this.trip.coordinateY);
        var geocoder = geocoder = new google.maps.Geocoder();
        geocoder.geocode({'latLng': latlng}, (results, status) => {
            if (status == google.maps.GeocoderStatus.OK) {
                console.log(results)
                if (results[0]) {
                    this.trip.place = results[0].formatted_address;
                }
            }
            // alert("Place: " + this.trip.place);
        });
    }

    openUserDetailsDialog(user) {
        let dialogRef = this.dialog.open(FriendDetailsComponent, {
            data: user,
            hasBackdrop: true,
            height: '80%',
            width: '80%',
        });
        dialogRef.afterClosed().subscribe(result => {
            this.selectedOption = result;
        });
    }

    search(term: string) {
        this.friendService.search(term)
            .subscribe(results => {
                this.friends = results;
            });
    }
}