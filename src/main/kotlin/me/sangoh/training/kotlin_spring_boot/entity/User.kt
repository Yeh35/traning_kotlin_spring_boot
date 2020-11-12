package me.sangoh.training.kotlin_spring_boot.entity

import javax.persistence.*

@Table(name = "user")
@Entity
class User (
        msrl: Long = 0,
        uid: String,
        name: String
) {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        var msrl: Long = 0
                private set

        @Column(nullable = false, unique = true, length = 30)
        var uid: String
                private set

        @Column(nullable = false, length = 100)
        var name: String
                private set

        init {
                this.msrl = msrl
                this.uid = uid
                this.name = name
        }
}

