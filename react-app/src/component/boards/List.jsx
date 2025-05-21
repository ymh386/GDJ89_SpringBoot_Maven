import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';

import { Container } from '@mui/material';
import { useState } from 'react';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useContext } from 'react';

import { Base_URL } from '../../contexts/UrlContext';
import { getHeaders, setHeaders } from '../../commons/UserManager';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

const rows = [
  createData('Frozen yoghurt', 159, 6.0, 24, 4.0),
  createData('Ice cream sandwich', 237, 9.0, 37, 4.3),
  createData('Eclair', 262, 16.0, 24, 6.0),
  createData('Cupcake', 305, 3.7, 67, 4.3),
  createData('Gingerbread', 356, 16.0, 49, 3.9),
];

export default function List() {

   //context
    const base_url = useContext(Base_URL);

    //글목록, Pager
     const [list, setList] = useState({pager:"", ar:[]});

     //검색어
     const [search2, setSearch2] = useState("");

     //페이지 번호
     const [page, setPage] = useState(1);

     //Component 재렌더링링
     const [flag, setFlag] = useState(false)

    useEffect(()=>{
        

        let params = new URLSearchParams();
        params.append('page', page)
        params.append('search', search2)

        let accessToken = window.sessionStorage.getItem("AccessToken")
        let refreshToken = window.localStorage.getItem("RefreshToken")

        fetch(`${base_url}/notices?${params}`, {
          headers: setHeaders()
        })
        .then(r=>{

          getHeaders(r)

          return  r.json()

        })
        .then(r=>{
            
            setList(r)
        })
    }, [flag])//[page, search2])


    function pageClick(e){
        let p= e.target.innerText;
        setPage(p)
        setFlag(!flag)
    }



  return (
    <Container maxWidth="lg">
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Num</StyledTableCell>
            <StyledTableCell align="right">Title</StyledTableCell>
            <StyledTableCell align="right">Writer</StyledTableCell>
            <StyledTableCell align="right">Date</StyledTableCell>
            <StyledTableCell align="right">Hit</StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {list.ar.map((row) => (
            <StyledTableRow key={row.boardNum}>
              <StyledTableCell component="th" scope="row">
                {row.boardNum}
              </StyledTableCell>
              <StyledTableCell align="right"><Link to={"/notice/detail"} state={{boardNum:row.boardNum}}> {row.boardTitle}</Link> </StyledTableCell>
              <StyledTableCell align="right">{row.userName}</StyledTableCell>
              <StyledTableCell align="right">{row.boardDate}</StyledTableCell>
              <StyledTableCell align="right">{row.boardHit}</StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>

    <Stack spacing={2}>
      <Pagination count={5} variant="outlined" shape="rounded" onChange={pageClick} />
    </Stack>

    </Container>
  );
}