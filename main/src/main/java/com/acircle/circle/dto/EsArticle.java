package com.acircle.circle.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Getter
@Setter
@Document(indexName = "jdbc", type = "jdbc",shards = 1,replicas = 0)
public class EsArticle implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Long id;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String text;
    @Field(value = "video_url",type = FieldType.Text)
    private String videoUrl;
    @Field(value = "user_id",type = FieldType.Long)
    private Long userId;
//    @Field(value = "create_time",type = FieldType.Date,format = DateFormat.custom, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @Field(value = "create_time",type = FieldType.Text)
    private String createTime;
    @Field(value = "logo_url",type = FieldType.Text)
    private String logoUrl;
}
