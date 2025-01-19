import React from 'react';
import { Modal, Box, Table, TableHead, TableRow, TableCell, TableBody, TextField, Button } from '@mui/material';
import {OrderDetailResponseDto} from "@/app/types/Order";


interface OrderDetailModalProps {
    open: boolean;
    onClose: () => void;
    orderDetail: OrderDetailResponseDto | null;
}

const OrderDetailModal: React.FC<OrderDetailModalProps> = ({ open, onClose, orderDetail }) => {
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
                <Box style={{ display: "flex", gap: "16px", flexDirection: "column" }}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>상품명</TableCell>
                                <TableCell>가격</TableCell>
                                <TableCell>수량</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {orderDetail?.products.map((product, index) => (
                                <TableRow key={index}>
                                    <TableCell>
                                        <TextField
                                            label="상품명"
                                            name={`name-${index}`}
                                            margin="normal"
                                            value={product.name}
                                            slotProps={{
                                                input: {
                                                    readOnly: true,
                                                },
                                            }}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <TextField
                                            label="가격"
                                            name={`price-${index}`}
                                            type="number"
                                            margin="normal"
                                            value={product.price}
                                            slotProps={{
                                                input: {
                                                    readOnly: true,
                                                },
                                            }}
                                        />
                                    </TableCell>
                                    <TableCell>
                                        <TextField
                                            label="수량"
                                            name={`quantity-${index}`}
                                            type="number"
                                            margin="normal"
                                            value={product.quantity}
                                            slotProps={{
                                                input: {
                                                    readOnly: true,
                                                },
                                            }}
                                        />
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Box>
                <Box style={{ display: 'flex', justifyContent: 'flex-end', gap: '8px' }}>
                    <Button variant="outlined" onClick={onClose}>
                        닫기
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default OrderDetailModal;
