package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DocumentListData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("fileName")
    @Expose
    private var fileName: String? = null

    @SerializedName("originalFileName")
    @Expose
    private var originalFileName: String? = null

    @SerializedName("filepath")
    @Expose
    private var filepath: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getFileName(): String? {
        return fileName
    }

    fun setFileName(fileName: String?) {
        this.fileName = fileName
    }

    fun getOriginalFileName(): String? {
        return originalFileName
    }

    fun setOriginalFileName(originalFileName: String?) {
        this.originalFileName = originalFileName
    }

    fun getFilepath(): String? {
        return filepath
    }

    fun setFilepath(filepath: String?) {
        this.filepath = filepath
    }
}