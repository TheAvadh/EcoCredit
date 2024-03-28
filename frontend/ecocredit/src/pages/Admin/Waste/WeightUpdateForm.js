import React, { useState } from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import Cookies from "js-cookie";

const WeightUpdateForm = () => {
    const [wasteId, setWasteId] = useState();
    const [weight, setWeight] = useState();
    const [defaultWeight] = useState(0.00);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = event.currentTarget;
    
        if (form.checkValidity() === true) {
          const requestBody = {
            weight
          };
    
          try {
            const response = await fetch(
              `${process.env.REACT_APP_BASE_URL}/admin/wastes/${wasteId}`,
              {
                method: "PATCH",
                mode: "cors",
                headers: {
                  Authorization: `Bearer ${Cookies.get("token")}`,
                  "Content-Type": "application/json",
                  Accept: "application/json",
                },
                body: JSON.stringify(requestBody),
              }
            );
    
            if (!response.ok) throw new Error("Failed to update weight");            
          } catch (error) {
            console.error("Update Weight Error:", error);
          }
          // Reload page after updating weight
          window.location.reload();
        }
      };


    return (
        <Row className="d-flex justify-content-center align-items-center">
            <Col lg={8}>
                <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
                    <h3 className="text-center text-ec-dark-green p-2">
                    Update Weight
                    </h3>
                    <Form onSubmit={handleSubmit}>
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
                            onChange={(e) => setWasteId(e.target.value)}
                        />
                        </FloatingLabel>
                        <FloatingLabel
                        controlId="weight"
                        label="Weight"
                        className="mb-2"
                        >
                        <Form.Control
                            type="number"
                            precision={2}
                            step={.01}
                            defaultValue={defaultWeight}
                            name="weight"
                            placeholder="weight"
                            required
                            value={weight}
                            onChange={(e) => setWeight(e.target.value)}
                        />
                        </FloatingLabel>
                        <Button
                        variant="ec-dark-green"
                        type="submit"
                        className="w-100 mt-1"
                        size="md"
                        >
                        Update
                        </Button>
                    </Form>
                </div>
            </Col>
      </Row>
    );
};


export default WeightUpdateForm;