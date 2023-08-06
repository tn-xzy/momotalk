package com.momotalk_v1.entity.context;

import com.momotalk_v1.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentImage extends UploadFile {
    private String imgPath;
    private String imgName;
    private String resolution;

    public ContentImage(UploadFile uploadFile) {
        super(uploadFile.getHash(), uploadFile.getSavename(), uploadFile.getSize(), uploadFile.getUnit());
    }
}
