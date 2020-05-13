package com.essensys.kotlinapplication.Model

//class for storage file
class StorageFile {
    private var fileName: String? = null
    private var filepath: String? = null

    fun getFileName(): String? {
        return fileName
    }

    fun setFileName(fileName: String?) {
        this.fileName = fileName
    }

    fun getFilepath(): String? {
        return filepath
    }

    fun setFilepath(filepath: String?) {
        this.filepath = filepath
    }
}