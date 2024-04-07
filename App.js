import {
  Box,
  Button,
  ChakraProvider,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Table,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';

function DowntimeSchedule() {
  const [formData, setFormData] = useState({
    date: '',
    startTime: '',
    endTime: '',
    reason: ''
  });
  const [downtimeData, setDowntimeData] = useState([]);

  useEffect(() => {
    // Fetch existing downtime data when component mounts
    fetchDowntimeData();
  }, []);

  const fetchDowntimeData = () => {
    // Example fetch request to fetch existing downtime data from the server
    // Replace this with your actual API endpoint to fetch data
    fetch('http://localhost:8080/api/downtime-schedule')
      .then(response => response.json())
      .then(data => {
        setDowntimeData(data);
      })
      .catch(error => console.error('Error fetching downtime data:', error));
  };

  const handleSubmit = async(event) => {
    event.preventDefault();
    console.log(formData);
    try {
      // Perform form submission logic here, e.g., sending data to the backend API
      const response = await fetch('http://localhost:8080/api/downtime-schedule', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });
      if (response.ok) {
        // If the response is successful (status code 2xx), you can handle it here
        // For example, you might want to display a success message to the user
        console.log('Form submitted successfully!');
        // You can also reset the form data after successful submission
        setFormData({
          date: '',
          startTime: '',
          endTime: '',
          reason: '',
          application:''
        });
      } else {
        // If the response is not successful (status code not 2xx), handle the error
        // For example, you might want to display an error message to the user
        console.error('Form submission failed.');
      }
    } catch (error) {
      // If there is a network error or any other error during the fetch operation, handle it here
      console.error('An error occurred while submitting the form:', error);
    }
    // Add your form submission logic here
    // Once submitted, refresh the downtime data
    fetchDowntimeData();
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  return (
    <ChakraProvider>
      <Box p={8}>
        <Heading as="h1" size="xl" mb={4}>
          Downtime Schedule
        </Heading>
        <form onSubmit={handleSubmit}>
          <FormControl id="date" mb={4}>
            <FormLabel>Date:</FormLabel>
            <Input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl id="startTime" mb={4}>
            <FormLabel>Start Time:</FormLabel>
            <Input
              type="time"
              name="startTime"
              value={formData.startTime}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl id="endTime" mb={4}>
            <FormLabel>End Time:</FormLabel>
            <Input
              type="time"
              name="endTime"
              value={formData.endTime}
              onChange={handleChange}
            />
          </FormControl>
          <FormControl id="reason" mb={4}>
            <FormLabel>Reason:</FormLabel>
            <Input
              type="text"
              name="reason"
              value={formData.reason}
              onChange={handleChange}
            />
          </FormControl>
          <Button colorScheme="blue" type="submit">
            Submit
          </Button>
        </form>

        <Box mt={8}>
          <Heading as="h2" size="lg" mb={4}>
            Existing Downtime Data
          </Heading>
          <Table variant="striped" colorScheme="gray">
            <Thead>
              <Tr>
                <Th>Date</Th>
                <Th>Start Time</Th>
                <Th>End Time</Th>
                <Th>Reason</Th>
              </Tr>
            </Thead>
            <Tbody>
              {downtimeData.map((dataItem, index) => (
                <Tr key={index}>
                  <Td>{dataItem.date}</Td>
                  <Td>{dataItem.startTime}</Td>
                  <Td>{dataItem.endTime}</Td>
                  <Td>{dataItem.reason}</Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </Box>
      </Box>
    </ChakraProvider>
  );
}

export default DowntimeSchedule;
