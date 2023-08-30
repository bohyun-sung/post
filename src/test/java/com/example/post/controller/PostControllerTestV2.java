//package com.example.post.controller;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.example.post.config.RestDocsConfig;
//import com.example.post.domain.post.entity.Post;
//import com.example.post.service.PostService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationContextProvider;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
//import org.springframework.restdocs.payload.PayloadDocumentation;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@WebMvcTest(controllers = PostController.class)
//@AutoConfigureRestDocs
//@MockBean(JpaMetamodelMappingContext.class)
//@Import(RestDocsConfig.class)
//public class PostControllerTestV2 {
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired protected MockMvc mvc;
//
//    @MockBean
//    protected PostService postService;
//
//    @Autowired
//    protected RestDocumentationResultHandler restDocs;
//
//    @BeforeEach
//    void setUp(final WebApplicationContext context,
//            final RestDocumentationContextProvider provider) {
//        this.mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))  // rest docs 설정 주입
//                .alwaysDo(MockMvcResultHandlers.print()) // andDo(print()) 코드 포함 -> 3번 문제 해결
//                .alwaysDo(restDocs) // pretty 패턴과 문서 디렉토리 명 정해준것 적용
//                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
//                .build();
//    }
//
//    @Test
//    void 게시판_목록_조회() throws Exception {
////        Customer customer = CustomerFixture.createCustomer();
//        PageImpl<Post> pageable = new PageImpl<>(List.of(), PageRequest.of(0, 10), 1);
//
//        given(postService.indexPost(any(Pageable.class))).willReturn(Page.empty());
//
//        mvc.perform(MockMvcRequestBuilders.get("/v1/post")
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(restDocs.document(
//                        PayloadDocumentation.responseFields(
//                                fieldWithPath("result").description("The data1 field"),
//                                fieldWithPath("data.content").description("The data1 field"),
//                                fieldWithPath("data.pageable").description("The data1 field"),
//                                fieldWithPath("data.last").description("The data1 field"),
//                                fieldWithPath("data.totalPages").description("The data1 field"),
//                                fieldWithPath("data.totalElements").description("The data1 field"),
//                                fieldWithPath("data.size").description("The data1 field"),
//                                fieldWithPath("data.number").description("The data1 field"),
//                                fieldWithPath("data.sort.empty").description("The data1 field"),
//                                fieldWithPath("data.sort.sorted").description("The data1 field"),
//                                fieldWithPath("data.sort.unsorted").description("The data1 field"),
//                                fieldWithPath("data.first").description("The data1 field"),
//                                fieldWithPath("data.numberOfElements").description("The data1 field"),
//                                fieldWithPath("data.empty").description("The data1 field")
//                        ))
//        );
//    }
//}
