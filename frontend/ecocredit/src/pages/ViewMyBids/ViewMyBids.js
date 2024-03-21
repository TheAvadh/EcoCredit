import React from "react";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";
import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";
import biodegradableURL from "../../assets/images/biodegradable.jpg";
import electronicsURL from "../../assets/images/electronics.jpg";
import glassURL from "../../assets/images/glass.jpg";
import mixedURL from "../../assets/images/mixed.jpg";
import paperURL from "../../assets/images/paper.jpg";
import plasticsURL from "../../assets/images/plastics.jpg";

const ViewMyBids = () => {
  const navigate = useNavigate();

  const redirectToBidPage = (bidId) => {
    navigate(`/recycler/active-bids/${bidId}`);
  };

  return (
    <Container fluid className="background-image">
      <Row xs={1} sm={2} lg={3} className="p-4">
        <Col className="d-flex g-3">
          <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
            <Card.Img variant="top" src={biodegradableURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Biodegradable</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-dark-green">Active</Badge>
                  </dd>
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
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="d-flex g-3">
          <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
            <Card.Img variant="top" src={electronicsURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Electronics</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-light-green" className="text-ec-dark-green">
                      Completed
                    </Badge>
                  </dd>
                </dl>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="d-flex g-3">
          <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
            <Card.Img variant="top" src={glassURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Glass</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-dark-green">Active</Badge>
                  </dd>
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
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="d-flex g-3">
          <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
            <Card.Img variant="top" src={mixedURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Mixed</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-light-green" className="text-ec-dark-green">
                      Completed
                    </Badge>
                  </dd>
                </dl>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>{" "}
            </Card.Footer>
          </Card>
        </Col>
        <Col className="d-flex g-3">
          <Card className="shadow-lg flex-fill rounded-4 bg-ec-grey text-ec-dark-green d-flex flex-column">
            <Card.Img variant="top" src={paperURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Paper</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-dark-green">Active</Badge>
                  </dd>
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
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>
            </Card.Footer>
          </Card>
        </Col>
        <Col className="d-flex g-3">
          <Card className="shadow-lg rounded-4 bg-ec-grey text-ec-dark-green">
            <Card.Img variant="top" src={plasticsURL} />
            <Card.Body className="d-flex flex-column">
              <Card.Title className="fs-3 fw-bold">Plastics</Card.Title>
              <Card.Text>
                <dl class="row">
                  <dt class="col-sm-5">Weight</dt>
                  <dd class="col-sm-7">5 KG</dd>

                  <dt class="col-sm-5">Current Highest Bid</dt>
                  <dd class="col-sm-7">10 CAD</dd>

                  <dt class="col-sm-5">Starting Bid</dt>
                  <dd class="col-sm-7">2 CAD</dd>

                  <dt class="col-sm-5">Status</dt>
                  <dd class="col-sm-7">
                    <Badge bg="ec-light-green" className="text-ec-dark-green">
                      Completed
                    </Badge>
                  </dd>
                </dl>
              </Card.Text>
            </Card.Body>
            <Card.Footer>
              <small className="text-muted">
                Created On: 2024-03-20T19:32:02
              </small>
            </Card.Footer>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ViewMyBids;
