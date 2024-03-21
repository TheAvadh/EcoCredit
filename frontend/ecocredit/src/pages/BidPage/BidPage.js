import React from "react";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import CenterContainer from "../../components/CenterContainer";
import biodegradableURL from "../../assets/images/biodegradable.jpg";
import electronicsURL from "../../assets/images/electronics.jpg";
import glassURL from "../../assets/images/glass.jpg";
import mixedURL from "../../assets/images/mixed.jpg";
import paperURL from "../../assets/images/paper.jpg";
import plasticsURL from "../../assets/images/plastics.jpg";

const BidPage = () => {
  return (
    <CenterContainer title="Place a Bid" colSize={8}>
      <Form className="mt-5">
        <Row>
          <Col lg={4} className="mb-4 text-center">
            <Image
              src={biodegradableURL}
              alt="biodegradble"
              style={{ width: "338px", height: "190px" }}
            />
          </Col>
          <Col lg={1}></Col>
          <Col lg={7}>
            <dl class="row text-end text-ec-dark-green fs-5 pb-4">
              <dt class="col-sm-7">Waste Type</dt>
              <dd class="col-sm-5">Biodegradable</dd>

              <dt class="col-sm-7">Weight</dt>
              <dd class="col-sm-5">5 KG</dd>

              <dt class="col-sm-7">Current Highest Bid</dt>
              <dd class="col-sm-5">10 CAD</dd>

              <dt class="col-sm-7">Starting Bid</dt>
              <dd class="col-sm-5">2 CAD</dd>
            </dl>
            <Row className="mt-5">
              <Col
                lg={7}
                className="d-flex align-items-center text-ec-dark-green"
              >
                <p className="fs-5 fw-bold">Next Bid Amount: 12$</p>
              </Col>
              <Col lg={5} className="d-grid pb-3">
                <Button
                  variant="ec-dark-green"
                  type="submit"
                  size="lg"
                  className="p-3"
                >
                  Bid for 12$
                </Button>
              </Col>
            </Row>
            <Row>
              <Col lg={7}>
                <Form.Floating className="mb-3">
                  <Form.Control
                    id="floatingInputCustom"
                    type="number"
                    placeholder="Bid Amount"
                    min={0}
                    step={1}
                  />
                  <label htmlFor="floatingInputCustom">
                    Enter Custom Bid Amount
                  </label>
                </Form.Floating>
              </Col>
              <Col lg={5} className="d-grid text-end mb-3">
                <Button variant="ec-dark-green" type="submit" size="lg">
                  Place Bid
                </Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </Form>
    </CenterContainer>
  );
};

export default BidPage;
