import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import biodegradableURL from "../../assets/images/biodegradable.jpg";
import electronicsURL from "../../assets/images/electronics.jpg";
import glassURL from "../../assets/images/glass.jpg";
import mixedURL from "../../assets/images/mixed.jpg";
import paperURL from "../../assets/images/paper.jpg";
import plasticsURL from "../../assets/images/plastics.jpg";

const ActiveBids = () => {
  const navigate = useNavigate();

  const redirectToBidPage = (bidId) => {
    navigate(`${bidId}`);
  };

  return (
    <Container fluid className="background-image">
      <Row xs={1} sm={2} lg={3} className="p-4">
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={biodegradableURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Biodegradable</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("1")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={electronicsURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Electronics</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("2")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={glassURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Glass</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("3")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={mixedURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Mixed</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("4")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={paperURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Paper</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("5")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={plasticsURL} />
            <Card.Body>
              <Card.Title className="fs-3 fw-bold">Plastics</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>
                </dl>
                <div className="text-end">
                  <Button
                    variant="ec-dark-green"
                    onClick={() => redirectToBidPage("6")}
                  >
                    Place a Bid
                  </Button>
                </div>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">Time Left: 20hr 12min</small>
            </Card.Footer>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ActiveBids;
