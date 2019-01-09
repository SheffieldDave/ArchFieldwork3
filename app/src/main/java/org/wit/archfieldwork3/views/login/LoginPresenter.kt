package org.wit.archfieldwork3.views.login

import org.wit.archfieldwork3.views.BasePresenter
import org.wit.archfieldwork3.views.BaseView
import org.wit.archfieldwork3.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    fun doLogin(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }

    fun doSignUp(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }
}