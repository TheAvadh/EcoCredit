import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import LoginForm from "../../components/LoginForm/LoginForm";
import SignupForm from "../../components/SignupForm";
import logoUrl from "../../assets/images/logo.png";
import recyclerURL from "../../assets/images/recycler.png";
import "./LoginSignup.css";

const LoginSignup = () => {
  const [activeView, setActiveView] = useState("login");

  return (
    <Container
      fluid
      className="d-flex justify-content-center align-items-center bg-ec-light-green px-0"
    >
      <Row className="w-100">
        <Col lg={6} className="mx-auto my-auto p-5 order-2 order-lg-1">
          <div className="tabs-container">
            <Button
              variant={
                activeView === "login" ? "ec-dark-green" : "ec-medium-green"
              }
              onClick={() => setActiveView("login")}
              className="tab-button"
            >
              Login
            </Button>
            <Button
              variant={
                activeView === "signup" ? "ec-dark-green" : "ec-medium-green"
              }
              onClick={() => setActiveView("signup")}
              className="tab-button"
            >
              Signup
            </Button>
          </div>

          {activeView === "login" ? (
            <LoginForm onLogin={() => {}} />
          ) : (
            <SignupForm onSignup={() => {}} />
          )}
        </Col>
        <Col lg={6} className="d-flex vh-100 justify-content-center align-items-center bg-ec-dark-green order-1 order-lg-2">
          <Row>
            <Col md={12}>
              <div className="mb-4 text-center">
                <img src={logoUrl} alt="EcoCredit Logo" className="logo" />
                <h1 className="title text-white">EcoCredit</h1>
              </div>
            </Col>
            <Col md={12} className="text-center">
              <img
                src={recyclerURL}
                alt="Recycler"
                className="recycler-image"
              />
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginSignup;
