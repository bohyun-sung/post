package com.example.post.utils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.Arrays;
import java.util.Map;
import javax.persistence.EnumType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

public class SnippetUtils {

    public static CustomResponseFieldsSnippet customResponseFields(String type,
            PayloadSubsectionExtractor<?> subsectionExtractor,
            Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }

//    public static FieldDescriptor[] enumConvertFieldDescriptor(EnumType[] enumTypes) {
//        return Arrays.stream(enumTypes)
//                .map(enumType -> fieldWithPath(enumType.getId()).description(enumType.getText()))
//                .toArray(FieldDescriptor[]::new);
//    }
}
