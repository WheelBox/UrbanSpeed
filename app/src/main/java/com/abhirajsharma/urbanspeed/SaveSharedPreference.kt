package com.abhirajsharma.urbanspeed

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


class SaveSharedPreference {

    val USER_NAME = UserInfo.userName
    val USER_ID = UserInfo.userId
    val MAIL_ID = UserInfo.userMail
    val USER_IMG = UserInfo.userImg

    private fun getSharedPreferences(ctx: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun setUser(ctx: Context?, userName: String, userId: String, mailId: String, userImg: String) {
        val editor = getSharedPreferences(ctx).edit()
        editor.putString(USER_NAME, userName)
        editor.putString(USER_ID, userId)
        editor.putString(MAIL_ID, mailId)
        editor.putString(USER_IMG, userImg)
        editor.apply()
    }

    fun getUserName(ctx: Context?): String? {
        return getSharedPreferences(ctx).getString(USER_NAME, "")
    }

    fun getUserID(ctx: Context?): String? {
        return getSharedPreferences(ctx).getString(USER_ID, "")
    }

    fun getUserMail(ctx: Context?): String? {
        return getSharedPreferences(ctx).getString(MAIL_ID, "")
    }

    fun getUserImg(ctx: Context?): String? {
        return getSharedPreferences(ctx).getString(USER_IMG, "")
    }

}