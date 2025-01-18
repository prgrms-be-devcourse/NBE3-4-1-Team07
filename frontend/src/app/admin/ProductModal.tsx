"use client";

import React from "react";
import {Box, Button, FormControl, Modal, TextField} from "@mui/material";
import Image from "next/image";
import {Product} from "@/app/types/Product";

type ProductModalProps = {
    isModalOpen: boolean;
    formValues: Product | null;
    handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleSave: () => void;
    handleClose: () => void;
};

export default function ProductModal({
                                         isModalOpen,
                                         formValues,
                                         handleInputChange,
                                         handleSave,
                                         handleClose,
                                     }: ProductModalProps) {
    return (
        <Modal
            open={isModalOpen}
            onClose={handleClose}
            aria-labelledby="product-modal-title"
            aria-describedby="product-modal-description"
        >
            <Box
                style={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    width: 600,
                    backgroundColor: "white",
                    border: "2px solid #000",
                    boxShadow: "24px",
                    padding: "16px",
                    display: "flex",
                    flexDirection: "column",
                    gap: "16px",
                }}
            >
                <Box style={{display: "flex", gap: "16px", alignItems: "flex-start"}}>
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
                                src={formValues.imgPath}
                                alt={formValues.name}
                                layout="fill"
                                objectFit="cover"
                            />
                        )}
                    </Box>
                    <Box style={{flex: 1}}>
                        <FormControl fullWidth>
                            <TextField
                                label="상품명"
                                name="name"
                                value={formValues?.name || ""}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="가격"
                                name="price"
                                type="number"
                                value={formValues?.price || ""}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                            <TextField
                                label="설명"
                                name="description"
                                value={formValues?.description || ""}
                                onChange={handleInputChange}
                                margin="normal"
                                multiline
                                rows={3}
                            />
                            <TextField
                                label="수량"
                                name="quantity"
                                type="number"
                                value={formValues?.quantity || ""}
                                onChange={handleInputChange}
                                margin="normal"
                            />
                        </FormControl>
                    </Box>
                </Box>
                {/* Bottom: Buttons */}
                <Box style={{display: "flex", justifyContent: "flex-end", gap: "8px"}}>
                    <Button variant="contained" color="primary" onClick={handleSave}>
                        저장
                    </Button>
                    <Button variant="outlined" onClick={handleClose}>
                        닫기
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
}
