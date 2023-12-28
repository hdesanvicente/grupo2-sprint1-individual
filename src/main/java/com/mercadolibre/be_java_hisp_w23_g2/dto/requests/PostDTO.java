package com.mercadolibre.be_java_hisp_w23_g2.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mercadolibre.be_java_hisp_w23_g2.dto.ProductBasicDTO;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"user_id", "post_id", "date", "product", "category", "price"})
public class PostDTO {

  @NotEmpty(message = "El id no puede estar vació")
  @Positive(message = "El id debe ser mayor a cero")
  @JsonProperty("user_id")
  private Integer userId;

  @NotEmpty(message = "La fecha no puede estar vacía")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate date;

  @Valid
  private ProductBasicDTO product;

  @NotEmpty(message = "La categoría no puede estar vacía")
  private String category;

  @NotEmpty(message = "El precio no puede estar vacío")
  @Size(max = 10000000, message = "El precio máximo por producto es de 10.000.000")
  private double price;
}
