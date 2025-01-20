import React, { useState } from 'react';
import { Modal, Box, FormControl, TextField, Button } from '@mui/material';
import Image from 'next/image';
import {Product, ProductRequestDto} from "@/app/types/Product";

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
    const [formValues, setFormValues] = useState<Product>({
        id: -1,
        name: '',
        price: 0,
        description: '',
        quantity: 0,
        imgPath: '',
    });

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
            imgPath: formValues.imgPath || "",
        }

        onSave(requestBody);
        onClose();
        setFormValues({
            id: -1,
            name: '',
            price: 0,
            description: '',
            quantity: 0,
            imgPath: '',
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
                        {formValues.imgPath && (
                            <Image
                                src={formValues.imgPath}
                                alt={formValues.name}
                                layout="fill"
                                objectFit="cover"
                            />
                        )}
                    </Box>
                    <Box style={{ flex: 1 }}>
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
                    <Button variant="outlined" onClick={onClose}>
                        닫기
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default ProductAddModal;
