import React, { useState } from 'react';
import { Modal, Box, FormControl, TextField, Button } from '@mui/material';
import Image from 'next/image';
import {ProductRequestDto} from "@/app/types/Product";
import ImageFileUploader from "@/app/admin/components/ImageFileUploader";

interface ProductAddModalProps {
    open: boolean;
    onClose: () => void;
    onSave: (product: ProductRequestDto) => void;
}

const ProductAddModal: React.FC<ProductAddModalProps> = ({
                                                             open,
                                                             onClose,
                                                             onSave
                                                         }) => {
    const [formValues, setFormValues] = useState<ProductRequestDto>({
        name: '',
        price: 0,
        description: '',
        quantity: 0,
        image: null,
    });

    const handleProductAddModalClose = () => {
        // 초기화
        setFormValues({
            name: '',
            price: 0,
            description: '',
            quantity: 0,
            image: null,
        });
        onClose();
    }

    const handleImageSelect = (file: File) => {
        setFormValues(prevValues => ({
            ...prevValues,
            image: file
        }));
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues(prevValues => ({
            ...prevValues,
            [name]: name === 'price' || name === 'quantity' ? Number(value) : value,
        }));
    };

    const handleSave = () => {
        if (formValues.name.trim() === '' || formValues.price <= 0) {
            alert('상품명과 가격은 필수 항목입니다.');
            return;
        }
        const requestBody: ProductRequestDto = {
            name: formValues.name,
            price: formValues.price,
            quantity: formValues.quantity,
            description: formValues.description || "",
            image: formValues.image || null,
        }

        onSave(requestBody);
        onClose();
        setFormValues({
            name: '',
            price: 0,
            description: '',
            quantity: 0,
            image: null,
        });
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box
                style={{
                    position: 'absolute',
                    top: '50%',
                    left: '50%',
                    transform: 'translate(-50%, -50%)',
                    width: 600,
                    backgroundColor: 'white',
                    border: '2px solid #000',
                    boxShadow: '24px',
                    padding: '16px',
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '16px',
                }}
            >
                <Box style={{ display: "flex", gap: "16px", alignItems: "flex-start" }}>
                    <div style={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                        gap: '8px'
                    }}>
                        <Box
                            style={{
                                width: "150px",
                                height: "150px",
                                position: "relative",
                                border: "1px solid #ddd",
                                borderRadius: "8px",
                                overflow: "hidden",
                            }}
                        >
                            {formValues.image && (
                                <Image
                                    src={URL.createObjectURL(formValues.image)}
                                    alt={formValues.name}
                                    width={150}
                                    height={150}
                                />
                            )}
                        </Box>
                        <Box style={{ textAlign: "center", marginTop: "8px" }}>
                            <label htmlFor="upload-button">
                                <input accept="image/*" style={{ display: 'none' }} id="upload-button" type="file" />
                                <ImageFileUploader onImageSelect={handleImageSelect} />
                            </label>
                        </Box>
                    </div>
                    <Box style={{flex: 1}}>
                        <FormControl fullWidth>
                            <TextField
                                label="상품명"
                                name="name"
                                value={formValues.name}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="가격"
                                name="price"
                                type="number"
                                value={formValues.price}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="설명"
                                name="description"
                                value={formValues.description}
                                onChange={handleInputChange}
                                margin="normal"
                                multiline
                                rows={3}
                            />
                            <TextField
                                label="수량"
                                name="quantity"
                                type="number"
                                value={formValues.quantity}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                        </FormControl>
                    </Box>
                </Box>
                <Box style={{ display: 'flex', justifyContent: 'flex-end', gap: '8px' }}>
                    <Button variant="contained" color="primary" onClick={handleSave}>
                        저장
                    </Button>
                    <Button variant="outlined" onClick={handleProductAddModalClose}>
                        닫기
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default ProductAddModal;
