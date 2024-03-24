import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import Button from "react-bootstrap/Button";
import Toast from "../../../../components/Toast/Toast";
import Cookies from "js-cookie";

const CreateBid = () => {
    const [wasteId, setWasteId] = useState("");
    const [date, setDate] = useState("");
    const [time, setTime] = useState("");
    const [showToast, setShowToast] = useState(false);
    const [toastMessage, setToastMessage] = useState("");
    const [toastType, setToastType] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();

        const bid = {
            wasteId: wasteId,
            dateTime: `${date}T${time}`
          };


        fetch(`${process.env.REACT_APP_BASE_URL}/admin/putwasteforbid`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              mode: "cors",
              Authorization: `Bearer ${Cookies.get("token")}`,
            },
            body: JSON.stringify(bid),
          })
            .then((response) => {
              if (response.ok) {
                setShowToast(true);
                setToastMessage("Created a bid");
                setToastType("success");
              } else {
                throw new Error("Failed to create a bid");
              }
            })
            .catch((error) => {
                console.error("Error:", error);
                setShowToast(error);
                setToastMessage("Failed to create a bid");
                setToastType("error");
            }
        );
    };

    const handleWasteIdChange = (e) => {
        setWasteId(e.target.value);
    };

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
                        <Form
                            onSubmit={handleSubmit}>
                            <Form.Group controlId="formWasteId">
                            <FloatingLabel
                            controlId="wasteId"
                            type="number"
                            label="Waste ID"
                            className="mb-2"
                            >
                            <Form.Control
                                type="number"
                                name="wasteId"
                                placeholder="Waste ID"
                                required
                                value={wasteId}
                                onChange={handleWasteIdChange}
                            />
                            </FloatingLabel>
                            </Form.Group>
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
                            <Button
                            variant="ec-dark-green"
                            type="submit"
                            className="w-100 mt-1"
                            size="md"
                            >
                            Create
                            </Button>
                        </Form>
                        <Toast
                            showToast={showToast}
                            setShowToast={setShowToast}
                            toastMessage={toastMessage}
                            toastType={toastType}
                        />
                    </div>
                </Col>
            </Row>
        </Container>
    );
};


export default CreateBid;