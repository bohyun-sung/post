package com.example.post.utils;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return Preprocessors.preprocessRequest(
// (1) 문서상 uri 를 기본값인 http://localhost:8080 에서 https://docs.api.com 으로 변경하기 위해 사용합니다.
                Preprocessors.modifyUris()
                        .scheme("https")
                        .host("bohyun.api.com")
                        .removePort(),
// (2) 문서의 request 을 예쁘게 출력하기 위해 사용합니다
                Preprocessors.prettyPrint());
    }

    static OperationResponsePreprocessor getDocumentResponse() {
//  (3) 문서의 response 를 예쁘게 출력하기 위해 사용합니다
        return Preprocessors.preprocessResponse(Preprocessors.prettyPrint()); // (3)
    }
}
