import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import FloatingLabel from "react-bootstrap/FloatingLabel";

const CreateBid = () => {
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");

    const handleDateChange = (e) => {
        setDate(e.target.value);
    };
    
    const handleTimeChange = (e) => {
    setTime(e.target.value);
    };
    
    return (
        <Container fluid className="background-image">
            <Row className="d-flex justify-content-center align-items-center">
                <Col lg={8} className="mt-5">
                    <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
                        <h1 className="text-center text-ec-dark-green p-2">
                        Create a Bid
                        </h1>
                        <Form>
                            <Form.Group controlId="formDate">
                            <FloatingLabel controlId="date" label="Date" className="mb-3">
                                <Form.Control
                                name="date"
                                type="date"
                                onChange={handleDateChange}
                                required
                                />
                            </FloatingLabel>
                            </Form.Group>

                            <Form.Group controlId="formTime">
                            <FloatingLabel controlId="time" label="Time" className="mb-3">
                                <Form.Control
                                name="time"
                                type="time"
                                onChange={handleTimeChange}
                                required
                                />
                            </FloatingLabel>
                            </Form.Group>
                        </Form>
                    </div>
                </Col>
            </Row>
        </Container>
    );
};


export default CreateBid;