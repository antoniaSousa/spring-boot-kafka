package io.github.antoniasousa.icompras.faturamento;

import io.github.antoniasousa.icompras.faturamento.bucket.BucketFile;
import io.github.antoniasousa.icompras.faturamento.bucket.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class apiBucketContoller {
    private final BucketService bucketService;

    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {

        }
    }
}
