import Carousel from 'react-bootstrap/Carousel';
import './AdminCarousel.css';
import RecyclingImage from "../../../assets/images/recycling-waste.png";
import BiddingImage from "../../../assets/images/bidding.webp";
import CreateBidImage from "../../../assets/images/create-a-bid.jpeg";

const AdminCarousel = () => {
    return (
        <Carousel>
        <Carousel.Item interval={1000}>
          <a href="/view-pickups">
            <img src={RecyclingImage}
            className='d-block w-100'
                alt='trash pickup'/>
          </a>
          <Carousel.Caption className="carousel-caption">
            <h3>View Pickups</h3>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={1000}>
          <a href="/view-bids">
            <img src={BiddingImage}  
            className = 'd-block w-100'
            alt='bids' />
          </a>
          <Carousel.Caption>
            <h3>View bids</h3>
          </Carousel.Caption>
        </Carousel.Item>
        <Carousel.Item interval={1000}>
          <a href="/create-a-bid">
            <img src={CreateBidImage} 
            className = 'd-block w-100'
            alt='create-bid' />
          </a>
          <Carousel.Caption>
            <h3>Create a Bid</h3>
          </Carousel.Caption>
        </Carousel.Item>
      </Carousel>
    );
};

export default AdminCarousel;