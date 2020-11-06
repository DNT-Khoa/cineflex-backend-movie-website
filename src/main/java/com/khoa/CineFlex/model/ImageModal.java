package com.khoa.CineFlex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Type should not be blank")
    private String type;

    @NotBlank(message = "Email should not be blank")
    private String email;

    @Lob
    @Column(length = 16777215)
    private byte[] picByte;
}
