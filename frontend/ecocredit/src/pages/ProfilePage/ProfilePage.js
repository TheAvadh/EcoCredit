import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import ProfileForm from "../../components/ProfileForm";
import profilePicURL from "../../assets/images/profilePic.jpg";
import "./ProfilePage.css";

const ProfilePage = () => {
  return (
    <Container className="min-vh-100 d-flex align-items-center justify-content-center bg-ec-light-green">
      <Row>
        <Col lg={3} className="mb-4 text-center">
          <Image
            src={profilePicURL}
            alt="Profile"
            roundedCircle
            style={{ width: "200px", height: "200px" }}
          />
        </Col>
        <Col lg={1}>
          <div className="profile-divider"></div>
        </Col>
        <ProfileForm />
      </Row>
    </Container>
  );
};

export default ProfilePage;
