package com.animasium.searchitcards.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "episodePOJO")
public class EpisodePOJO {
    @PrimaryKey(autoGenerate = true)
    public int id ;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "episodeNo")
    String episodeNo;
    @ColumnInfo(name = "time")
    long time;


    public EpisodePOJO(int id, String name, String episodeNo) {
        this.id = id;
        this.name = name;
        this.episodeNo = episodeNo;
    }
    @Ignore
    public EpisodePOJO( String name, String episodeNo) {
        this.name = name;
        this.episodeNo = episodeNo;
    }
//    @Ignore
//    public EpisodePOJO( String name, long time) {
//        this.name = name;
//        this.time = time;
//    }

    EpisodePOJO(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpisodeNo() {
        return episodeNo;
    }

    public void setEpisodeNo(String episodeNo) {
        this.episodeNo = episodeNo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
