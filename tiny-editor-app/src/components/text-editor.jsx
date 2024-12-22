import React, {useState} from 'react'
import {alpha, ThemeProvider} from '@mui/material/styles'
import CssBaseline from '@mui/material/CssBaseline'
import Box from '@mui/material/Box'
import Container from '@mui/material/Container'
import Paper from '@mui/material/Paper'
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemText from '@mui/material/ListItemText'
import {Button, Divider, Grid2} from "@mui/material";
import {
    copy,
    cut,
    deleteText,
    insertText,
    moveSelection,
    paste, redo,
    replayRecord,
    startRecord,
    stopRecord, undo
} from "../service/editor-service";
import {hackerTheme} from "../theme";

export default function TextEditor() {
    const [inputText, setInputText] = useState('')
    const [outputText, setOutputText] = useState('')
    const [clipboardContent, setClipboardContent] = useState('')
    const [history, setHistory] = useState([])
    const [isRecording, setIsRecording] = useState(false);

    const [beginIndex, setBeginIndex] = useState('');
    const [endIndex, setEndIndex] = useState('');
    const [beginIndexError, setBeginIndexError] = useState(false);
    const [endIndexError, setEndIndexError] = useState(false);

    const [currentBeginIndex, setCurrentBeginIndex] = useState(0);
    const [currentEndIndex, setCurrentEndIndex] = useState(0);

    const handleBeginIndexChange = (e) => {
        const value = e.target.value;
        if (value === '' || (parseInt(value) >= 0 && (endIndex === '' || parseInt(value) <= parseInt(endIndex)))) {
            setBeginIndex(value);
            setBeginIndexError(false);
            if (endIndex === '') {
                setEndIndex(value);
            }
        } else {
            setBeginIndexError(true);
        }
    };

    const handleEndIndexChange = (e) => {
        const value = e.target.value;
        if (value === '' || (parseInt(value) >= 0 && (beginIndex === '' || parseInt(value) >= parseInt(beginIndex)))) {
            setEndIndex(value);
            setEndIndexError(false);
        } else {
            setEndIndexError(true);
        }
    };
    function updateStatus(res) {
        if (res) {
            setOutputText(res.currentBufferContent)
            setCurrentBeginIndex(res.selected.beginIndex)
            setCurrentEndIndex(res.selected.endIndex)
            setClipboardContent(res.clipboard)
            setHistory(history => [`Engine updated: ${new Date().toLocaleTimeString()}\n
              ${JSON.stringify(res, null, 2)}
            `, ...history])
        }
    }

    const handleRefresh = () => {
        return moveSelection(outputText.length,outputText.length).then(res => updateStatus(res))
    }

    const handleInsert = () => {
        return insertText(inputText).then(res => {
            updateStatus(res);
        })
    }

    const handleDelete = () => {
        return deleteText().then(res => updateStatus(res))
    }

    const handleMoveSelection = () => {
        if (beginIndex === '' || endIndex === '') {
            setBeginIndexError(true);
            setEndIndexError(true);
            return;
        }
        if (beginIndex > outputText.length || endIndex > outputText.length) {
            alert('Index exceeds the length of the current text')
            return;
        }
        return moveSelection(beginIndex, endIndex).then(res => updateStatus(res)).then(
            () => console.log('now selected:' + beginIndex + ',' + endIndex)
        )
    }
    const handleCopy = () => {
        copy().then(res => updateStatus(res))
    }

    const handlePaste = () => {
        paste().then(res => updateStatus(res))
    }

    const handleCut = () => {
        cut().then(res => updateStatus(res))
    }

    const handleStartRecord = () => {
        setIsRecording(true);
        return stopRecord().then(() => {
            startRecord().then(res => updateStatus(res))
        })
    }

    const handleStopRecord = () => {
        setIsRecording(false);
        return stopRecord().then(res => updateStatus(res))
    }

    const handleReplayRecord = () => {
        return stopRecord().then(() => {
            replayRecord().then(res => updateStatus(res))
        })

    }


    const handleUndo = () => {
        return undo().then(res => updateStatus(res))
    }

    const handleRedo = () => {
        return redo().then(res => updateStatus(res))
    }
    return (
        <ThemeProvider theme={hackerTheme}>
            <CssBaseline/>
            <Container
                sx={{height: '100vh', width: '100vw'}}>
                <Box
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        background: 'linear-gradient(135deg, #000000 0%, #001100 100%)',
                        p: 3,
                    }}
                >
                    <Typography
                        variant="h2"
                        component="h1"
                        gutterBottom
                        align="center"
                        sx={{
                            mb: 4,
                            color: '#00FF00',
                            fontWeight: 'bold',
                            textShadow: '0 0 10px rgba(0, 255, 0, 0.7)',
                        }}
                    >Tiny Text Editor</Typography>
                    {/* Use Grid to make it responsive */}
                    <Grid2 container spacing={3}
                           sx={{display: 'flex', flexDirection: 'row'}}>
                        <Grid2 container size={6} spacing={3}
                               sx={{display: 'flex', flexDirection: 'column'}}>
                            <Grid2 item size={12}>
                                <Paper elevation={3} sx={{p: 2}}>
                                    <Typography variant="h5" gutterBottom sx={{color: '#00FF00'}}>
                                        Console
                                    </Typography>
                                    <Box sx={{gap: 2}}>
                                        <Button onClick={handleStartRecord} disabled={isRecording}>Start
                                            Recording</Button>
                                        <Button onClick={handleStopRecord} disabled={!isRecording}>Stop
                                            Recording</Button>
                                        <Button onClick={handleReplayRecord} disabled={isRecording}>Play
                                            Recording</Button>
                                    </Box>
                                    <Box sx={{ paddingY: 1 }}> {/* 调整数值以适应你的需求 */}
                                        <Divider sx={{ borderColor: hackerTheme.palette.text.secondary }} />
                                    </Box>
                                    <Box>
                                        <Button onClick={handleDelete}>Delete</Button>
                                        <Button onClick={handleCut} >Cut</Button>
                                        <Button onClick={handleCopy} >Copy</Button>
                                        <Button onClick={handlePaste} >Paste</Button>
                                        <Button onClick={handleUndo} >Undo</Button>
                                        <Button onClick={handleRedo} >Redo</Button>
                                    </Box>
                                    <Box display="flex" sx={{ gap: 2, alignItems: 'center'}}>
                                        <TextField
                                            label="Begin Index"
                                            type="number"
                                            value={beginIndex}
                                            onChange={handleBeginIndexChange}
                                            variant="outlined"
                                            margin="normal"
                                            error={beginIndexError}
                                            sx = {{flex: 1, height: '56px'}}
                                        />
                                        <TextField
                                            label="End Index"
                                            type="number"
                                            value={endIndex}
                                            onChange={handleEndIndexChange}
                                            variant="outlined"
                                            margin="normal"
                                            error={endIndexError}
                                            sx = {{flex: 1, height: '56px'}}
                                        />
                                        <Button sx = {{height: '56px'}} onClick={handleMoveSelection}>Move Selection</Button>
                                    </Box>
                                    <Box display="flex">
                                        <TextField
                                            value={inputText}
                                            onChange={(e) => setInputText(e.target.value)}
                                            placeholder="Enter your text here..."
                                            variant="outlined"
                                            sx={{
                                                '& .MuiInputBase-input': {
                                                    color: '#00FF00',
                                                    fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                },
                                                '& .MuiOutlinedInput-root': {
                                                    backgroundColor: 'rgba(0, 20, 0, 0.3)',
                                                },
                                                flex: 1,
                                                mr: 2,
                                            }}
                                        />
                                        <Button onClick={handleInsert}>Insert</Button>
                                    </Box>
                                </Paper>
                            </Grid2>
                            <Grid2 item size={12}>
                                <Paper elevation={3} sx={{display: 'flex', flexDirection: 'column', p: 2}}>
                                    <Typography variant="h5" gutterBottom sx={{color: '#00FF00'}}>
                                        Editor State
                                    </Typography>
                                    <Box sx={{ display: 'flex', gap: 2, mb: 2, alignItems: 'center', justifyContent: 'space-between' }}>
                                        <Box sx={{display: 'flex', gap: 2}}>
                                            <Box>
                                                Begin Index: {currentBeginIndex.toString() || ''}
                                            </Box>
                                            <Box>
                                                End Index: {currentEndIndex.toString() || ''}
                                            </Box>
                                        </Box>
                                        <Box>
                                            <Button onClick={handleRefresh}>Refresh</Button>
                                        </Box>
                                    </Box>
                                    <Box sx={{flex: 1, display: 'flex', flexDirection: 'column', gap: 2}}>
                                        <Paper
                                            variant="outlined"
                                            sx={{
                                                flex: 1,
                                                width: '100%',
                                                maxHeight: '100px',
                                                p: 2,
                                                wordBreak: 'break-word',
                                                overflowY: 'auto',
                                                fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                fontSize: '0.9rem',
                                                color: '#00FF00',
                                                backgroundColor: alpha('#000000', 0.5),
                                            }}
                                        >
                                            <Typography variant="subtitle2" gutterBottom sx={{color: '#00FF00'}}>
                                                Current Content:
                                            </Typography>
                                            {outputText || 'No content'}
                                        </Paper>
                                        <Paper
                                            variant="outlined"
                                            sx={{
                                                p: 2,
                                                fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                fontSize: '0.9rem',
                                                color: '#00FF00',
                                                backgroundColor: alpha('#000000', 0.5),
                                                wordBreak: 'break-word',
                                                overflowY: 'auto',
                                                maxHeight: '100px',
                                            }}
                                        >
                                            <Typography variant="subtitle2" gutterBottom sx={{color: '#00FF00'}}>
                                                Clipboard Content:
                                            </Typography>
                                            {clipboardContent || 'Empty'}
                                        </Paper>
                                    </Box>
                                </Paper>
                            </Grid2>
                        </Grid2>
                        <Grid2 item size={6}>
                            <Paper elevation={3} sx={{height: '632px', display: 'flex', flexDirection: 'column', p: 2}}>
                                <Typography variant="h5" gutterBottom sx={{color: '#00FF00'}}>
                                    Editor History
                                </Typography>

                                <Paper
                                    variant="outlined"
                                    sx={{
                                        flex: 1,
                                        p: 2,
                                        overflowY: 'auto',
                                        backgroundColor: alpha('#000000', 0.5),
                                    }}
                                >
                                    <List dense>
                                        {history.map((event, index) => (
                                            <ListItem key={index} sx={{py: 0.5}}>
                                                <ListItemText
                                                    primary={event}
                                                    sx={{
                                                        '& .MuiListItemText-primary': {
                                                            color: '#00FF00',
                                                            fontFamily: '"Fira Code", "Roboto Mono", monospace',
                                                            fontSize: '0.8rem',
                                                        },
                                                    }}
                                                />
                                            </ListItem>
                                        ))}
                                    </List>
                                </Paper>
                            </Paper>
                        </Grid2>
                    </Grid2>
                </Box>
            </Container>
        </ThemeProvider>
    )
}


