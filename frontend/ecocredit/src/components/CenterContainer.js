import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import "../pages/CustomerDashboard/CustomerDashboard";

const CenterContainer = ({ children, title }) => {
  return (
    <Container
      fluid
      className="d-flex vh-100 justify-content-center align-items-center background-image"
    >
      <Row className="w-100">
        <Col lg={6} className="mx-auto">
          <div className="shadow-lg rounded-4 bg-ec-grey p-4 mb-5">
            <h1 className="text-center text-ec-dark-green mb-4">{title}</h1>
            {children}
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default CenterContainer;
