import * as React from 'react';
import { styled } from '@mui/material/styles';
import Button from '@mui/material/Button';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';


interface ImageFileUploaderProps {
    onImageSelect: (image: File) => void;
}

const VisuallyHiddenInput = styled('input')({
    clip: 'rect(0 0 0 0)',
    clipPath: 'inset(50%)',
    height: 1,
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    whiteSpace: 'nowrap',
    width: 1,
});

const ImageFileUploader: React.FC<ImageFileUploaderProps> = ({ onImageSelect }) => {
    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file) {
            onImageSelect(file); // 파일 객체를 부모로 전달
        }
    };

    return (
        <Button
            component="label"
    role={undefined}
    variant="contained"
    tabIndex={-1}
    startIcon={<CloudUploadIcon />}
>
    Upload
    <VisuallyHiddenInput
    type="file"
    onChange={handleFileChange}
    multiple
    accept={"image/*"}
    />
    </Button>
);
}

export default ImageFileUploader;