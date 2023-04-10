import * as React from 'react';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import InboxIcon from '@mui/icons-material/Inbox';
import DraftsIcon from '@mui/icons-material/Drafts';

export default function BasicList(props) {

  return (
    <Box sx={{ width: '30%', maxWidth: 300 }}>
      <nav aria-label="main mailbox folders">
        <List sx={{ bgcolor: 'background.paper'}}>
          <ListItem disablePadding onClick={() => props.setData("1")}>
            <ListItemButton>
              <ListItemIcon>
                <InboxIcon />
              </ListItemIcon>
              <ListItemText primary="내정보" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding onClick={() => props.setData("2")}>
            <ListItemButton>
              <ListItemIcon>
                <DraftsIcon />
              </ListItemIcon>
              <ListItemText primary="내가쓴글" />
            </ListItemButton>
          </ListItem>
          <ListItem disablePadding onClick={() => props.setData("3")}>
            <ListItemButton>
              <ListItemIcon>
                <DraftsIcon />
              </ListItemIcon>
              <ListItemText primary="북마크" />
            </ListItemButton>
          </ListItem>
        </List>
      </nav>
      <Divider />
    </Box>
  );
}