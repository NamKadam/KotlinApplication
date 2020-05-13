package com.essensys.kotlinapplication.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class ActionLogsData {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("work_id")
    @Expose
    private var workId: String? = null

    @SerializedName("type_service_sales")
    @Expose
    private var typeServiceSales: String? = null

    @SerializedName("call_schedule_ondate")
    @Expose
    private var callScheduleOndate: String? = null

    @SerializedName("actionByUserId")
    @Expose
    private var actionByUserId: String? = null

    @SerializedName("actionOnDateTime")
    @Expose
    private var actionOnDateTime: String? = null

    @SerializedName("comment")
    @Expose
    private var comment: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("documentList")
    @Expose
    private var documentList: ArrayList<DocumentListData?>? = null
    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    private var isSelected = false


    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getWorkId(): String? {
        return workId
    }

    fun setWorkId(workId: String?) {
        this.workId = workId
    }

    fun getTypeServiceSales(): String? {
        return typeServiceSales
    }

    fun setTypeServiceSales(typeServiceSales: String?) {
        this.typeServiceSales = typeServiceSales
    }

    fun getCallScheduleOndate(): String? {
        return callScheduleOndate
    }

    fun setCallScheduleOndate(callScheduleOndate: String?) {
        this.callScheduleOndate = callScheduleOndate
    }

    fun getActionByUserId(): String? {
        return actionByUserId
    }

    fun setActionByUserId(actionByUserId: String?) {
        this.actionByUserId = actionByUserId
    }

    fun getActionOnDateTime(): String? {
        return actionOnDateTime
    }

    fun setActionOnDateTime(actionOnDateTime: String?) {
        this.actionOnDateTime = actionOnDateTime
    }

    fun getComment(): String? {
        return comment
    }

    fun setComment(comment: String?) {
        this.comment = comment
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDocumentList(): ArrayList<DocumentListData?>? {
        return documentList
    }

    fun setDocumentList(documentList: ArrayList<DocumentListData?>?) {
        this.documentList = documentList
    }
}