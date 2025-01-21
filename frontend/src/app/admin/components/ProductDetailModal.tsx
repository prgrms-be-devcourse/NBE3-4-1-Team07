import React from 'react';
import { Modal, Box, FormControl, TextField, Button } from '@mui/material';
import Image from 'next/image';
import { Product } from "@/app/types/Product";

interface ProductDetailModalProps {
    open: boolean;
    onClose: () => void;
    formValues: Product | null;
    onSave: (product: Product) => void;
    onInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const ProductDetailModal: React.FC<ProductDetailModalProps> = ({
                                                                   open,
                                                                   onClose,
                                                                   formValues,
                                                                   onSave,
                                                                   onInputChange
                                                               }) => {
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
                        {formValues?.imgPath && (
                            <Image
                                src={"http://localhost:8080" + formValues.imgPath}
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
                                value={formValues?.name || ""}
                                onChange={onInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="가격"
                                name="price"
                                type="number"
                                value={formValues?.price || ""}
                                onChange={onInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="설명"
                                name="description"
                                value={formValues?.description || ""}
                                onChange={onInputChange}
                                margin="normal"
                                multiline
                                rows={3}
                            />
                            <TextField
                                label="수량"
                                name="quantity"
                                type="number"
                                value={formValues?.quantity || ""}
                                onChange={onInputChange}
                                margin="normal"
                            />
                        </FormControl>
                    </Box>
                </Box>
                <Box style={{ display: 'flex', justifyContent: 'flex-end', gap: '8px' }}>
                    <Button variant="contained" color="primary" onClick={() => onSave(formValues!)}>
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

export default ProductDetailModal;
