import React, { useEffect } from 'react';
import { useSearchParams, useNavigate  } from 'react-router-dom';
import Cookies from 'js-cookie';

const SuccessPage = () => {
    const [searchParams] = useSearchParams(); // Get pickupId from URL params
    
    const navigate = useNavigate();

    useEffect(() => {
        const confirmPickup = async () => {
            try {
                await fetch(`${process.env.REACT_APP_BASE_URL}/pickups/confirm?pickupId=${searchParams.get("pickupId")}`, {
                    method: "GET",
                    headers: {
                      Authorization: `Bearer ${Cookies.get("token")}`,
                      "Content-Type": "application/json",
                    },
                  });
                console.log('Pickup confirmed successfully!');
                // Redirect to the desired URL after confirming pickup
                navigate(`/customer/pickup-status`);
            } catch (error) {
                console.error('Error confirming pickup:', error);
            }
        };

        if (searchParams) {
            confirmPickup();
        }
    }, [searchParams, navigate]);

    return (
        <div>
        </div>
    );
};

export default SuccessPage;
