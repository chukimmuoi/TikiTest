package com.chukimmuoi.mbase.api

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author : Chu Kim Muoi
 * @Skype : chukimmuoi
 * @Mobile : +84 373 672 505
 * @Email : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 3/21/20.
 */
// https://blog.mindorks.com/implement-search-using-rxjava-operators-c8882b64fe1d

object RxSearchObservable {

    fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })

        return subject
    }

    fun fromView(editText: EditText): Observable<String> {
        val subject = PublishSubject.create<String>()

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //subject.onComplete()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                subject.onNext(s.toString())
            }
        })

        return subject
    }
}