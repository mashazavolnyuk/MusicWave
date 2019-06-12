package com.mashazavolnyuk.musicwave.loader

import android.content.Context
import android.provider.BaseColumns
import android.provider.MediaStore

import com.mashazavolnyuk.musicwave.data.Song

import java.util.ArrayList
import java.util.Collections

object SongLoader {

    private val BASE_SELECTION = arrayOf(BaseColumns._ID, // 0
            MediaStore.Audio.AudioColumns.TITLE, // 1
            MediaStore.Audio.AudioColumns.TRACK, // 2
            MediaStore.Audio.AudioColumns.YEAR, // 3
            MediaStore.Audio.AudioColumns.DURATION, // 4
            MediaStore.Audio.AudioColumns.DATA, // 5
            MediaStore.Audio.AudioColumns.DATE_MODIFIED, // 6
            MediaStore.Audio.AudioColumns.ALBUM_ID, // 7
            MediaStore.Audio.AudioColumns.ALBUM, // 8
            MediaStore.Audio.AudioColumns.ARTIST_ID, // 9
            MediaStore.Audio.AudioColumns.ARTIST)// 10

    fun getSongList(context: Context): ArrayList<Song> {
        val songList = ArrayList<Song>()
        val musicResolver = context.contentResolver
        val musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = musicResolver.query(musicUri, BASE_SELECTION, null, null, null)
        //iterate over results if valid
        if (cursor != null && cursor.moveToFirst()) {
            do {
                 with(cursor) {
                    val id = getInt(0)
                    val title = getString(1)
                    val trackNumber = getInt(2)
                    val year = getInt(3)
                    val duration = getLong(4)
                    val data = getString(5)
                    val dateModified = getLong(6)
                    val albumId = getInt(7)
                    val albumName = getString(8)
                    val artistId = getInt(9)
                    val artistName = getString(10)
                     songList.add(Song(id, title, trackNumber, year, duration, data, dateModified, albumId, albumName, artistId, artistName))
                }
            } while (cursor.moveToNext())
            cursor.close()
        }
        Collections.sort(songList) { a, b -> a.title.compareTo(b.title) }
        return songList
    }
}
