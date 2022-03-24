package com.animasium.searchitcards.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EpisodeDao {

    @Query("Select * from episodePOJO order by id DESC")
    List<EpisodePOJO> getEpisodeList();
    @Insert
    void insertEpi(EpisodePOJO episodePOJO);
    @Update
    void updateEpi(EpisodePOJO episodePOJO);
    @Delete
    void deleteEpi(EpisodePOJO episodePOJO);
    @Query("DELETE FROM episodePOJO WHERE episodeNo = :episodeNo and name= :name")
    void deleteEpiByNameAndEpisodeNo(String name, String episodeNo);
    @Query("SELECT * FROM episodePOJO where name = :name and episodeNo= :episodeNo")
    EpisodePOJO getEpiByNameAndEpisodeNo(String name, String episodeNo);


    @Query("SELECT * FROM episodePOJO where name = :name and time= :time")
    EpisodePOJO getEpiByNameAndTime(String name, long time);

    //recent changes
    @Query("SELECT * FROM episodePOJO where name = :name and episodeNo= :episodeNo")
    int checkEpiByNameAndEpisodeNo(String name, String episodeNo);

    @Query("SELECT * FROM episodePOJO where name = :name")
    int checkEpiByName(String name);
}
