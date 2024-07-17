package nl.jovmit.androiddevs.domain.auth.data

class UserBuilder {

    private var userId: String = ""
    private var email: String = ""
    private var about: String = ""

    fun withUserId(userId: String) = apply {
        this.userId = userId
    }

    fun withEmail(email: String) = apply {
        this.email = email
    }

    fun withAbout(about: String) = apply {
        this.about = about
    }

    fun build(): User {
        return User(userId, email, about)
    }

    companion object {
        fun aUser() = UserBuilder()
    }
}