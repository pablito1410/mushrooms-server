package pl.polsl.mushrooms.application.dao;

import pl.polsl.mushrooms.application.model.MushroomSpecies;

/**
 * Created by pawel_zaqkxkn on 25.04.2017.
 */
public interface MushroomSpeciesDao {
    MushroomSpecies findOne(long mushroomSpieceId);
}
