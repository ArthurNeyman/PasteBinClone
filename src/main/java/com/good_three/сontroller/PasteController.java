package com.good_three.сontroller;

import com.good_three.dto.CommentDTO;
import com.good_three.dto.PasteDTO;
import com.good_three.dto.ResponseStatusDTO;
import com.good_three.services.IMapperService;
import com.good_three.services.IPasteService;
import com.good_three.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Доступно всем пользователям
@RestController
@RequestMapping("paste")
public class PasteController {

    @Autowired
    private IPasteService pasteService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMapperService mapper;

    @GetMapping("")//Получить последние 10 паст public
    public ResponseStatusDTO get() {

        ResponseStatusDTO res=new ResponseStatusDTO();

        try{
            res.setData(pasteService.getLastTenPastes());
            res.addMessage(("Получены послежние 10 паст из публичного доступа"));
        }catch(Exception e){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }
        return res;
    }

    @GetMapping("/{hashCode}")//Получение пасту по Хэшкоду
    public ResponseStatusDTO getByHashCode(@PathVariable String hashCode) {

        ResponseStatusDTO res=new ResponseStatusDTO();

        try{
            res.setData(pasteService.getPasteByHashCode(hashCode));
            if(res.getData()== null) {
                res.setStatus(HttpStatus.NO_CONTENT);
                 res.addMessage(("Пасты с таким хэшкодом не существует или ее срок жизни истек"));
            } else{
                res.addMessage(("Паста получена"));
            }
        }catch(Exception e){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }
        return res;
    }

    @PostMapping("/publication")//Публикация пасты
    public ResponseStatusDTO save(@RequestBody PasteDTO pasteDTO) {

        ResponseStatusDTO res=new ResponseStatusDTO();

        try{

            pasteDTO.setUser(userService.getUser());

            if(pasteDTO.getId() == 0){
                res.setData(pasteService.savePaste(pasteDTO));
                res.addMessage(("Паста опубликована")); }
            else{
                res.addMessage(("Паста не опубликована"));
            }

        }catch(Exception e){
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.addMessage(("Ошибка на стороне сервера"));
        }

        return res;

    }

    @PostMapping("/comment")//добавление комментария к пасте
    public ResponseStatusDTO addComment(@RequestBody CommentDTO commentDTO) {

        ResponseStatusDTO res=new ResponseStatusDTO();

        commentDTO.setUserName(userService.getUser()!=null ? userService.getUser().getUsername() : null );

        if(pasteService.saveComment(commentDTO))
            res.addMessage("Ваш комментарий добавлен");
        else
            res.addMessage("Ваш комментарий не добавлен");

        return res;
    }

    @GetMapping("/search/{str}") //поиск паст по коду или названию
    public ResponseStatusDTO searchPaste(@PathVariable String str) {

        ResponseStatusDTO res=new ResponseStatusDTO();

        List<PasteDTO> dtos=pasteService.searchPastes(str);

        res.setData(dtos);

        if(dtos.size()==0)
            res.addMessage("Пасты по запросу не обнаружены");
        else
            res.addMessage("Найлено"+ dtos.size()+" по запросу");

        return  res;
    }


}

