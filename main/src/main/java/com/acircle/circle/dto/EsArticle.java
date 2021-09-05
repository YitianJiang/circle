package com.acircle.circle.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Document(indexName = "jdbc", type = "jdbc",shards = 1,replicas = 0)
public class EsArticle implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Long id;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String text;
    private String videoUrl;
    private Long userId;
    private Date createTime;
    private String logo_url;
}
