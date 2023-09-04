package com.example.post.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.post.domain.post.dto.PostDto;
import com.example.post.domain.post.dto.requset.PostCreateAndUpdateReq;
import com.example.post.domain.post.entity.Post;
import com.example.post.domain.reply.entity.Reply;
import com.example.post.service.PostService;
import com.example.post.utils.ApiDocumentUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.ParameterDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private PostService postService;


    @BeforeEach
    public void setUp(WebApplicationContext context) {
//        UserDetails userDetails = CustomerFixture.createCustomerPrincipal();
//
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
//        this.mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .defaultRequest(post("/**").with(csrf()))
//                .defaultRequest(patch("/**").with(csrf()))
//                .defaultRequest(delete("/**").with(csrf()))
//                .build();
    }
//    @BeforeEach
//    void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
//        Filter documentationConfiguration = documentationConfiguration(restDocumentation)
//                .operationPreprocessors()
//                .withRequestDefaults(prettyPrint())
//                .withResponseDefaults(prettyPrint());
//
//        this.spec = new RequestSpecBuilder()
//                .addFilter(documentationConfiguration)
//                .build();
//    }

    @DisplayName("[GET] 게시판_목록_조회")
    @WithMockUser
    @Test
    void 게시판_목록_조회() throws Exception {

        ParameterDescriptor parameterDescriptor = new ParameterDescriptor();
        // response
        FieldDescriptor[] fieldDescriptor = new FieldDescriptor[]{
                fieldWithPath("test").description("test")
        };

        FieldDescriptor[] fieldDescriptor2 = new FieldDescriptor[]{
                fieldWithPath("test2").description("test2")
        };

        fieldDescriptor.
        given(postService.indexPost(any(Pageable.class))).willReturn(Page.empty());

        mvc.perform(get("/v1/post")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post",
                        ApiDocumentUtils.getDocumentRequest(),
                        ApiDocumentUtils.getDocumentResponse(),
                        responseFields(
                                fieldWithPath("result").description("The data1 field"),
                                fieldWithPath("data.content").description("The data1 field"),
                                fieldWithPath("data.pageable").description("The data1 field"),
                                fieldWithPath("data.last").description("The data1 field"),
                                fieldWithPath("data.totalPages").description("The data1 field"),
                                fieldWithPath("data.totalElements").description("The data1 field"),
                                fieldWithPath("data.size").description("The data1 field"),
                                fieldWithPath("data.number").description("The data1 field"),
                                fieldWithPath("data.sort.empty").description("The data1 field"),
                                fieldWithPath("data.sort.sorted").description("The data1 field"),
                                fieldWithPath("data.sort.unsorted").description("The data1 field"),
                                fieldWithPath("data.first").description("The data1 field"),
                                fieldWithPath("data.numberOfElements").description("The data1 field"),
                                fieldWithPath("data.empty").description("The data1 field")
                        )
                    )
                );
    }

    @DisplayName("[GET] 게시판_상세_조회")
    @WithMockUser
    @Test
    void 게시판_상세_조회() throws Exception {

        Post post = Post.of("제목1", "본문1");
        Reply reply = Reply.of("이름", "ㅅㄷㄴㅅ", post);
        post.addReply(reply);
        PostDto postDto = PostDto.fromWithReply(post);

        given(postService.showPost(1L)).willReturn(postDto);

        mvc.perform(RestDocumentationRequestBuilders.get("/v1/post/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(
                        document("post_show",
                                pathParameters(
                                        parameterWithName("id").description("Post Id")
                                ),
                                responseFields(
                                    fieldWithPath("result").description("성공 여부"),
                                    fieldWithPath("data.id").description("post id"),
                                    fieldWithPath("data.title").description("post 제목"),
                                    fieldWithPath("data.content").description("post 본문"),
                                    fieldWithPath("data.rgdt").description("post 등록일"),
                                    fieldWithPath("data.updt").description("post 수정일"),
                                    fieldWithPath("data.replys[0].writer").description("reply 등록자"),
                                    fieldWithPath("data.replys[0].content").description("reply 내용"),
                                    fieldWithPath("data.replys[0].rgdt").description("reply 등록일")
                                )
                        )
                );
    }

    @DisplayName("[POST] 게시판_등록")
    @WithMockUser
    @Test
    void 게시판_등록() throws Exception {
        PostCreateAndUpdateReq request = new PostCreateAndUpdateReq();
        ReflectionTestUtils.setField(request, "title", "test_제목");
        ReflectionTestUtils.setField(request, "content", "test_본문");

        mvc.perform(post("/v1/post")
                        .header(HttpHeaders.AUTHORIZATION.toLowerCase(), "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(
                        document("post_create",
                        responseFields(
                                fieldWithPath("result").description("성공 여부")
                        )
                    )
                );
    }

    @DisplayName("[PATCH] 게시판_수정")
    @WithMockUser
    @Test
    void 게시판_수정() throws Exception {
        PostCreateAndUpdateReq request = new PostCreateAndUpdateReq();
        ReflectionTestUtils.setField(request, "title", "test_변경_제목");
        ReflectionTestUtils.setField(request, "content", "test_변경_본문");

//        given(postService.updatePost(1L, request)).willReturn();

        mvc.perform(patch("/v1/post/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION.toLowerCase(), "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(
                        document("post_update",
                                responseFields(
                                        fieldWithPath("result").description("성공 여부")
                                )
                        )
                );
    }

    @DisplayName("[DELETE] 게시판_삭제")
    @WithMockUser
    @Test
    void 게시판_삭제() throws Exception {
        mvc.perform(delete("/v1/post/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION.toLowerCase(), "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        )
                .andExpect(status().isOk())
                .andDo(
                        document("post_delete",
                                responseFields(
                                        fieldWithPath("result").description("성공 여부")
                                )
                        )
                );
    }

    protected final LinksSnippet pagingLinks = links(
            linkWithRel("result"),
            linkWithRel("data.content"),
            linkWithRel("data.pageable"),
            linkWithRel("data.last"),
            linkWithRel("data.totalPages"),
            linkWithRel("data.totalElements"),
            linkWithRel("data.size"),
            linkWithRel("data.number"),
            linkWithRel("data.sort.empty"),
            linkWithRel("data.sort.sorted"),
            linkWithRel("data.sort.unsorted"),
            linkWithRel("data.first"),
            linkWithRel("data.numberOfElements"),
            linkWithRel("data.empty")
    );
}
