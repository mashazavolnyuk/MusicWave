package com.mashazavolnyuk.musicwave.data

import android.os.Parcel
import android.os.Parcelable

class Song {

    val id: Int
    val title: String
    val trackNumber: Int
    val year: Int
    val duration: Long
    val data: String
    val dateModified: Long
    val albumId: Int
    val albumName: String
    val artistId: Int
    val artistName: String


    constructor(id: Int, title: String, trackNumber: Int, year: Int, duration: Long, data: String, dateModified: Long, albumId: Int, albumName: String, artistId: Int, artistName: String) {
        this.id = id
        this.title = title
        this.trackNumber = trackNumber
        this.year = year
        this.duration = duration
        this.data = data
        this.dateModified = dateModified
        this.albumId = albumId
        this.albumName = albumName
        this.artistId = artistId
        this.artistName = artistName

    }

    protected constructor(`in`: Parcel) {
        id = `in`.readInt()
        title = `in`.readString()
        trackNumber = `in`.readInt()
        year = `in`.readInt()
        duration = `in`.readLong()
        data = `in`.readString()
        dateModified = `in`.readLong()
        albumId = `in`.readInt()
        albumName = `in`.readString()
        artistId = `in`.readInt()
        artistName = `in`.readString()
    }

    companion object {
        val EMPTY_SONG = Song(-1, "", -1, -1, -1, "", -1, -1, "", -1, "")

        val CREATOR: Parcelable.Creator<Song> = object : Parcelable.Creator<Song> {
            override fun createFromParcel(`in`: Parcel): Song {
                return Song(`in`)
            }

            override fun newArray(size: Int): Array<Song?> {
                return arrayOfNulls(size)
            }
        }
    }
}
