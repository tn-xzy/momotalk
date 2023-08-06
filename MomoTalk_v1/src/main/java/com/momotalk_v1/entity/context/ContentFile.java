package com.momotalk_v1.entity.context;

import com.momotalk_v1.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentFile extends UploadFile {
    private String filepath;//前端获取文件的路径
    private String filename;//文件原名

    public ContentFile(UploadFile uploadFile) {
        super(uploadFile.getHash(), uploadFile.getSavename(), uploadFile.getSize(), uploadFile.getUnit());
    }
}
