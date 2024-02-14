import React, { useState } from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import LoginForm from "../../components/LoginForm";
import SignupForm from "../../components/SignupForm";
import logoUrl from "../../assets/images/logo.png";
import "./LoginSignup.css";

const LoginSignup = () => {
  const [activeView, setActiveView] = useState("login");

  return (
    <Container className="d-flex vh-100 justify-content-center align-items-center">
      <Row className="w-100">
        <Col lg={6} className="mx-auto">
          <div className="mb-4 text-center">
            <img src={logoUrl} alt="EcoCredit Logo" className="logo" />
            <h1 className="title">EcoCredit</h1>
          </div>
          <div className="tabs-container">
            <Button
              variant={
                activeView === "login" ? "ec-dark-green" : "ec-light-green"
              }
              onClick={() => setActiveView("login")}
              className="tab-button"
            >
              Login
            </Button>
            <Button
              variant={
                activeView === "signup" ? "ec-dark-green" : "ec-light-green"
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
      </Row>
    </Container>
  );
};

export default LoginSignup;
