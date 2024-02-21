import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

const ProfileForm = ({
  profileData,
  handleInputChange,
  handleAddressChange,
  handleSave,
  editMode,
  setEditMode,
}) => (
  <Col lg={8}>
    <Form onSubmit={handleSave}>
      <Row>
        <Col>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="firstName"
          >
            <Form.Label>First Name :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="firstName"
              value={profileData.firstName}
              onChange={handleInputChange}
              disabled={!editMode}
            />
          </Form.Group>
        </Col>
        <Col>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="lastName"
          >
            <Form.Label>Last Name :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="lastName"
              value={profileData.lastName}
              onChange={handleInputChange}
              disabled={!editMode}
            />
          </Form.Group>
        </Col>
      </Row>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="email"
      >
        <Form.Label>Email Address :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="email"
          value={profileData.email}
          onChange={handleInputChange}
          disabled={!editMode}
        />
      </Form.Group>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="phoneNumber"
      >
        <Form.Label>Phone Number :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="phoneNumber"
          value={profileData.phoneNumber}
          onChange={handleInputChange}
          disabled={!editMode}
        />
      </Form.Group>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="address.street"
      >
        <Form.Label>Street :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="street"
          value={profileData.address.street}
          onChange={handleAddressChange}
          disabled={!editMode}
        />
      </Form.Group>

      <Row>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.city"
          >
            <Form.Label>City :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="city"
              value={profileData.address.city}
              onChange={handleAddressChange}
              disabled={!editMode}
            />
          </Form.Group>
        </Col>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.province"
          >
            <Form.Label>Province :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="province"
              value={profileData.address.province}
              onChange={handleAddressChange}
              disabled={!editMode}
            />
          </Form.Group>
        </Col>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.postalCode"
          >
            <Form.Label>Postal Code :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="postalCode"
              value={profileData.address.postalCode}
              onChange={handleAddressChange}
              disabled={!editMode}
            />
          </Form.Group>
        </Col>
      </Row>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="address.country"
      >
        <Form.Label>Country :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="country"
          value={profileData.address.country}
          onChange={handleAddressChange}
          disabled={!editMode}
        />
      </Form.Group>

      <Button
        variant="ec-grey"
        className="mt-2 me-2 text-ec-dark-green"
        size="lg"
        onClick={() => setEditMode(!editMode)}
      >
        {editMode ? "Cancel" : "Edit"}
      </Button>

      {editMode && (
        <Button
          variant="ec-dark-green text-ec-grey"
          className="mt-2"
          size="lg"
          type="submit"
        >
          Save
        </Button>
      )}
    </Form>
  </Col>
);

export default ProfileForm;
