import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import logoUrl from "../assets/images/logo.png";

const FormContainer = ({ children, title }) => {
  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center bg-ec-light-green">
      <Row className="w-100">
        <Col lg={6} className="mx-auto">
          <div className="mb-4 text-center">
            <img src={logoUrl} alt="EcoCredit Logo" className="logo" />
            <h1 className="title">EcoCredit</h1>
          </div>
          <div className="form-container bg-ec-grey">
            <h2 className="text-center text-ec-dark-green mb-4">{title}</h2>
            {children}
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default FormContainer;
