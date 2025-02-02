import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { io } from "socket.io-client";

const SOCKET_URL = "http://localhost:8080/ws"; // WebSocket server URL

function EventDetails() {
    const { id } = useParams();
    const [event, setEvent] = useState(null);
    const [attendeeCount, setAttendeeCount] = useState(0);
    const socket = io(SOCKET_URL, { transports: ["websocket"] });

    useEffect(() => {
        const fetchEvent = async () => {
            const token = localStorage.getItem("token");
            const response = await axios.get(`http://localhost:8080/api/events/${id}`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            setEvent(response.data);
            setAttendeeCount(response.data.attendees.length);
        };

        fetchEvent();

        // Subscribe to WebSocket updates for attendee count
        socket.emit("subscribe", { eventId: id });

        socket.on(`event/${id}`, (count) => {
            setAttendeeCount(count);
        });

        return () => {
            socket.disconnect();
        };
    }, [id, socket]);

    const handleJoinEvent = async () => {
        const token = localStorage.getItem("token");
        await axios.put(`http://localhost:8080/api/events/${id}/join`, {}, {
            headers: { Authorization: `Bearer ${token}` },
        });
    };

    if (!event) return <p>Loading...</p>;

    return (
        <div>
            <h2>{event.name}</h2>
            <p>{event.description}</p>
            <p><strong>Date:</strong> {new Date(event.dateTime).toLocaleString()}</p>
            <p><strong>Attendees:</strong> {attendeeCount}</p>
            <button onClick={handleJoinEvent}>Join Event</button>
        </div>
    );
}

export default EventDetails;
