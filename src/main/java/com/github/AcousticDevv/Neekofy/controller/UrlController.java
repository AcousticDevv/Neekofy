/*
 * Copyright (c) 2023 AcousticDevv.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal n the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * HE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.AcousticDevv.Neekofy.controller;

import com.github.AcousticDevv.Neekofy.model.Url;
import com.github.AcousticDevv.Neekofy.repository.UrlRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/u")
public class UrlController {
    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/add-url")
    public String addUrl(@Valid Url url, BindingResult result, Model model) {
        if (result.hasErrors()) return "index";
        if (urlRepository.existsById(url.getId())) {
            model.addAttribute("additionalErrorLog", "Url with that ID already exists!");
            return "index";
        }
        urlRepository.save(url);
        return "redirect:/u/" + url.getId();
    }

    @GetMapping("/{id}")
    public String getUrl(@PathVariable String id, Model model) {
        Optional<Url> url = urlRepository.findById(id);
        return url.map(value -> "redirect:" + value.getRedirectUrl()).orElse("errors/404");
    }
}
