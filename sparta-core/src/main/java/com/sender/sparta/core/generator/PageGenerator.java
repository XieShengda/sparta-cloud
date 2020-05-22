package com.sender.sparta.core.generator;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sender.sparta.core.dto.Page;
import com.sender.sparta.core.vo.PageModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class PageGenerator {
    public PageModel generate(Page page, Supplier<List<?>> supplier) {
        PageHelper.startPage(page.current(), page.size());
        PageInfo<?> pageInfo = new PageInfo<>(supplier.get());
        return new PageModel()
                .records(pageInfo.getList())
                .current(page.current())
                .size(page.size())
                .total(pageInfo.getTotal())
                .pages(pageInfo.getPages());
    }
}