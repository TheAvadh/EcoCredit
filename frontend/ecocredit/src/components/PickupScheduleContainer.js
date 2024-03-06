import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

const PickupScheduleContainer = ({ children, title }) => {
  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center">
      <Row className="w-100">
        <Col lg={6} className="mx-auto">
          <div className="form-container bg-ec-grey">
            <h2 className="text-center text-ec-dark-green mb-4">{title}</h2>
            {children}
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default PickupScheduleContainer;
