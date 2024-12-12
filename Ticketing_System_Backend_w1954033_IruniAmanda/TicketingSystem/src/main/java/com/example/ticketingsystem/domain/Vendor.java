package com.example.ticketingsystem.domain;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="vendor")
@Data
public class Vendor implements Serializable{
    @Serial
    private  static  final  long serialVersionUID = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private  Long id;

    @Column(name = "user_name",nullable = false,length = 30)
    private String userName;

    @Column(name = "first_name",length = 30)
    private String firstName;

    @Column(name = "last_name",length = 30)
    private  String lastName;

    @Column(name = "email",nullable = false,length =50)
    private String email;

    @Column(name = "address",length = 50)
    private  String address;

    @Column(name = "mobile_no",length = 10)
    private Long mobileNo;

    @Column(name = "password",nullable = false,length =20)
    private String password;

}
