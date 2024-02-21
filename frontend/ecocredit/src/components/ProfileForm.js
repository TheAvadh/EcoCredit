import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

const ProfileForm = () => (
  <Col lg={8}>
    <Form>
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
            />
          </Form.Group>
        </Col>
        <Col>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="firstName"
          >
            <Form.Label>Last Name :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="firstName"
            />
          </Form.Group>
        </Col>
      </Row>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="firstName"
      >
        <Form.Label>Email Address :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="firstName"
        />
      </Form.Group>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="firstName"
      >
        <Form.Label>Phone Number :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="firstName"
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
        />
      </Form.Group>

      <Row>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.street"
          >
            <Form.Label>City :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="street"
            />
          </Form.Group>
        </Col>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.street"
          >
            <Form.Label>Province :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="street"
            />
          </Form.Group>
        </Col>
        <Col md={4}>
          <Form.Group
            className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
            controlId="address.street"
          >
            <Form.Label>Postal Code :</Form.Label>
            <Form.Control
              className="text-ec-dark-green fs-5"
              type="text"
              name="street"
            />
          </Form.Group>
        </Col>
      </Row>

      <Form.Group
        className="mb-3 text-ec-dark-green lead fs-4 fw-bold"
        controlId="address.street"
      >
        <Form.Label>Country :</Form.Label>
        <Form.Control
          className="text-ec-dark-green fs-5"
          type="text"
          name="street"
        />
      </Form.Group>

      <Button
        variant="ec-grey"
        className="mt-2 me-2 text-ec-dark-green"
        size="lg"
      >
        Cancel
      </Button>

      <Button
        variant="ec-dark-green text-ec-grey"
        className="mt-2"
        size="lg"
        type="submit"
      >
        Save
      </Button>
    </Form>
  </Col>
);

export default ProfileForm;
